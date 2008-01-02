/*
 *  mGoban - GUI for Go
 *  Copyright (C) 2007  sanpo
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package go;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

/**
 * カナダ方式のみに対応した対局時計。<br>
 *<br>
 * メモ<br>
 * nngs サーバーは時間と着手数をかえす。<br>
 * メイン時間内の時 "45 -1"<br>
 * 秒読み時間内の時 "350 15"<br>
 * 時間無制限の時 "0 -1"<br>
 *
 * gtp はメイン時間、秒読み時間、着手数で時間を管理するが、設定はできてもその値を取得することはできないみたい。<br>
 * 時間無制限は 秒読み時間 >0 で 着手数 = 0 で表現する<br>
 */

/*
 * TODO
 * wing で考慮時間になってからどのような時間表示かかえってくるのか未確認。
 * コードも未。
 */
public class GoClock {

    private GoClockListener listener;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> future;
    private Runnable task;
    private GoTimer blackTimer;
    private GoTimer whiteTimer;
    private GoTimer currentTimer;
    private long startTime;
    private boolean noTimeLimit;

    public GoClock() {				// 時間無制限
        init(0.0, 0.0, 0, 0.0);
        noTimeLimit = true;
    }

    public GoClock(double mainSec, double byoYomiSec, int byoYomiStones) {
        init(mainSec, byoYomiSec, byoYomiStones, 0.0);
        if (mainSec == 0.0 && byoYomiSec == 0.0) {
            noTimeLimit = true;
        } else {
            noTimeLimit = false;
        }
    }

    public GoClock(double mainSec, double byoYomiSec, int byoYomiStones, double kouryoSec) {
        init(mainSec, byoYomiSec, byoYomiStones, kouryoSec);
        if (mainSec == 0.0 && byoYomiSec == 0.0 && kouryoSec == 0.0) {
            noTimeLimit = true;
        } else {
            noTimeLimit = false;
        }
    }

    private void init(double mainSec, double byoYomiSec, int byoYomiStones, double kouryoSec) {
        System.out.println("GoClock:" + mainSec + ":" + byoYomiSec + ":" + byoYomiStones + ":" + kouryoSec);
        this.listener = null;

        this.scheduler = Executors.newScheduledThreadPool(1);
        this.task = new GoTimerTask();

        blackTimer = new GoTimer(mainSec, byoYomiSec, byoYomiStones, kouryoSec);
        whiteTimer = new GoTimer(mainSec, byoYomiSec, byoYomiStones, kouryoSec);
        currentTimer = null;

        this.startTime = 0;
    }

    private void fireClockChanged() {
        if (listener != null) {
            listener.clockChanged(this);
        }
    }

    private void fireClockSoundTiming() {
        if (listener != null) {
            listener.clockSoundTiming(this);
        }
    }
    
    private void fireTimeOver(GoColor color){
        if (listener != null) {
            listener.clockTimeOver(this, color);
        }
    }
    
    private void checkTimeOver() {
        if (blackTimer.isOver()) {
            fireTimeOver(GoColor.BLACK);
        }
        if (whiteTimer.isOver()) {
            fireTimeOver(GoColor.WHITE);
        }
    }

    public void setListener(GoClockListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        listener = null;
    }

    // nngs の形式
    public void adjust(GoColor color, double sec, int stones) {
        if (color == GoColor.BLACK) {
            blackTimer.adjust(sec, stones);
        } else {
            whiteTimer.adjust(sec, stones);
        }

        fireClockChanged();
        checkTimeOver();
    }
    
    public boolean isCounting(GoColor color){
        if((color.isBlack() && currentTimer == blackTimer)
                || (color.isWhite() && currentTimer == whiteTimer)){
            return true;
        }else{
            return false;
        }
    }

    public boolean isNoTimeLimit() {
        return noTimeLimit;
    }

    public boolean isBlinking(GoColor color) {
        if (color.isBlack()) {
            return (currentTimer == blackTimer) && blackTimer.isBlinkTiming();
        } else {
            return (currentTimer == whiteTimer) && whiteTimer.isBlinkTiming();
        }
    }

    public void start(GoColor color) {
        //System.out.println("GoClock.start():" + noTimeLimit);
        if (future != null) {
            future.cancel(true);
        }

        if (color.isBlack()) {
            currentTimer = blackTimer;
        } else {
            currentTimer = whiteTimer;
        }

        if (noTimeLimit == false) {
            future = scheduler.scheduleAtFixedRate(task, 1000, 1000, TimeUnit.MILLISECONDS);
        }

        startTime = System.currentTimeMillis();
    }

    // 直前からの秒数をかえす。
    public double stop() {
        if(noTimeLimit){
            return 0.0;
        }
        
        if(currentTimer != null){
            currentTimer.stop();
        }
        fireClockChanged();
        
        long stopTime = System.currentTimeMillis();
        long d = stopTime - startTime;

        // System.out.println("GoClock.stop():" + (int) (d / 1000.0));

        if (future != null) {
            future.cancel(true);
        }

        return d / 1000.0;
    }

    public void shutdown() {
        System.out.println("GoClock.shutdown");
        scheduler.shutdownNow();
    }

    public void decrease() {
        currentTimer.decrease();
        // System.out.println("GoClock.decrease()");
        fireClockChanged();

        if (currentTimer.isSoundTiming()) {
            fireClockSoundTiming();
        }

        checkTimeOver();
    }

    public String getTimeString(GoColor color) {
        if (color == GoColor.BLACK) {
            return blackTimer.getTimeString();
        } else {
            return whiteTimer.getTimeString();
        }
    }

    public double getSeconds(GoColor color){
        if(color.isBlack()){
            return blackTimer.getSeconds();
        }else{
            return whiteTimer.getSeconds();
        }
    }
    
    // メインタイム中は ゼロを返す（gtp の仕様）
    public int getStones(GoColor color){
        if(color.isBlack()){
            return blackTimer.getStones();
        }else{
            return whiteTimer.getStones();
        }
    }
    
    class GoTimerTask extends TimerTask {

        public GoTimerTask() {
        }

        public void run() {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    decrease();
                }
            });
        }
    }

    class GoTimer {
        // 設定
        private double mainSec;
        private double byoYomiSec;
        private int byoYomiStones;
        private double kouryoSec;
        
        private boolean noLimit;
        
        // 現在の値
        private double currentMainSec;
        private double currentByoYomiSec;
        private int currentByoYomiStones;
        private double currentKouryoSec;

        public GoTimer() {       // 時間無制限
            this.mainSec = 0.0;
            this.byoYomiSec = 0.0;
            this.byoYomiStones = 0;
            this.kouryoSec = 0.0;
            this.noLimit = true;
            
            this.currentMainSec = 0.0;
            this.currentByoYomiSec = 0.0;
            this.currentByoYomiStones = 0;
            this.currentKouryoSec = 0.0;
        }

        public GoTimer(double mainSec, double byoYomiSec, int byoYomiStones, double kouryoSec) {
            this.mainSec = mainSec;
            this.byoYomiSec = byoYomiSec;
            this.byoYomiStones = byoYomiStones;
            this.kouryoSec = kouryoSec;
            if(byoYomiStones <= 0){
                this.noLimit = true;
            }else{
                this.noLimit = false;
            }
            
            this.currentMainSec = mainSec;
            this.currentByoYomiSec = byoYomiSec;
            this.currentByoYomiStones = byoYomiStones;
            this.currentKouryoSec = kouryoSec;
        }

        public double getSeconds(){
            if(currentMainSec > 0){
                return currentMainSec;
            }else if(currentByoYomiSec > 0){
                return currentByoYomiSec;
            }else{
                return currentKouryoSec;
            }
        }
        
        public int getStones(){
            if(currentMainSec > 0){
                return 0;
            }else{
                return currentByoYomiStones;
            }
        }
        
        private boolean isInMainTime(){
            if(currentMainSec > 0){
                return true;
            }else{
                return false;
            }
        }
        
        private boolean isInByoYomiTime(){
            if(currentMainSec == 0 && currentByoYomiStones > 0){
                return true;
            }else{
                return false;
            }
        }
        
        private boolean isInKouryoTime(){
            if(currentMainSec == 0 && currentByoYomiSec == 0 && currentKouryoSec > 0){
                return true;
            }else{
                return false;
            }
        }
        
        public void stop(){
            if(isInByoYomiTime()){
                currentByoYomiStones -= 1;
                if(currentByoYomiStones == 0){
                    currentByoYomiSec = byoYomiSec;
                    currentByoYomiStones = byoYomiStones;
                }
            }
        }

        // nngs の形式
        /*
         * 時間無制限の時 "0 -1"
         * メイン時間内の時 "45 -1"
         * 秒読み時間内の時 "350 15"
         */
        public void adjust(double sec, int stones) {
            if (sec == 0 && stones == -1) {
                noLimit = true;
            } else if (sec > 0 && stones == -1) {
                currentMainSec = sec;
            } else {
                currentMainSec = 0.0;
                currentByoYomiSec = sec;
                currentByoYomiStones = stones;
            }
        }

        public void decrease() {
            if (noLimit) {
                return;
            }

            if (isInMainTime()) {
                currentMainSec -= 1;
            } else if (isInByoYomiTime()) {
                currentByoYomiSec -= 1;
            } else {
                currentKouryoSec -= 1;
            }
        }

        public boolean isSoundTiming() {
            if (isInMainTime() && byoYomiSec == 0
                    && (currentMainSec == 30 || currentMainSec == 20 || currentMainSec <= 10)) {
                // 秒読み時間のないメイン時間だけの対局の時
                return true;
            } else if (isInByoYomiTime() 
                    && (currentByoYomiSec == 30 || currentByoYomiSec == 20 || currentByoYomiSec <= 10)) {
                return true;
            } else if (isInKouryoTime()
                    && (currentKouryoSec == 30 || currentKouryoSec == 20 || currentKouryoSec <= 10)) {
                return true;
            }
            return false;
        }

        public boolean isBlinkTiming() {
            if (isInMainTime()  && byoYomiSec == 0 && currentMainSec < 30) {
                return true;
            } else if (isInByoYomiTime() && currentByoYomiSec < 30) {
                return true;
            } else if (isInKouryoTime() && currentKouryoSec < 30) {
                return true;
            }
            return false;
        }

        public boolean isOver() {
            if (currentMainSec == 0 && currentByoYomiSec == 0 && currentKouryoSec == 0) {
                return true;
            } else {
                return false;
            }
        }

        public String getTimeString() {
            String s;
            if (noLimit && currentMainSec == 0.0 && currentByoYomiSec == 0.0 && currentKouryoSec == 0.0) {
                s = "-";
            } else if (isInMainTime()) {
                s = secToString((int) currentMainSec);
            } else if (isInByoYomiTime()) {
                StringBuffer sb = new StringBuffer();
                sb.append(secToString(currentByoYomiSec));
                sb.append(" (").append(Integer.toString(currentByoYomiStones)).append(")");
                s = sb.toString();
            } else {
                s = secToString((int) currentKouryoSec);
            }
            return s;
        }

        private String secToString(double sec) {
            int isec;
            if (sec < 0) {
                isec = -(int) sec;
            } else {
                isec = (int) sec;
            }

            int m = isec / 60;
            int s = isec - m * 60;

            StringBuffer str = new StringBuffer();
            if (sec < 0) {
                str.append("-");
            }
            str.append(Integer.toString(m));
            str.append(":");
            if (s < 10) {
                str.append("0");
            }
            str.append(Integer.toString(s));

            return str.toString();
        }
    }
}