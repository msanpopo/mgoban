/*
 *  mGoban - GUI for Go
 *  Copyright (C) 2007, 2009  sanpo
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

package wing.translator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wing.Block;
import wing.message.AdjournRequestMessage;
import wing.message.CreatingMatchMessage;
import wing.message.DeclareMessage;
import wing.message.DeclineMatchMessage;
import wing.message.MatchRequestMessage;
import wing.message.MatchUpdateMessage;
import wing.message.Message;
import wing.message.RawMessage;
import wing.message.RemoveVertexMessage;
import wing.message.ResultAdjournMessage;
import wing.message.ResultForfeitMessage;
import wing.message.ResultNormalMessage;
import wing.message.ResultResignMessage;
import wing.message.ScoreModeMessage;
import wing.message.StatsMessage;
import wing.message.SuggestMessage;

public class InfoTranslator extends Translator {
    
    private static final Pattern pStats = Pattern.compile("Player:\\s+(.*)\\s*");
    private static final Pattern pSuggest = Pattern.compile("(.*)と互角な対局をするには.*");
    
    private static final Pattern pResult = Pattern.compile("\\{Game\\s+(\\d+):\\s+(\\S+) vs (\\S+) : (.*?)\\}");
    
    private static final Pattern pRemove = Pattern.compile("Removing @ (.*)");
    
    private static final Pattern pMatch = Pattern.compile("(\\w+) \\[(\\d+)x\\d+\\] in (\\d+) minutes requested with (\\S+) as (\\S+)\\.");

    public Message translate(Block block) {        
        String firstline = block.get(0);
        
        Matcher mStats = pStats.matcher(firstline);
        Matcher mSuggest = pSuggest.matcher(firstline);
        Matcher mResult = pResult.matcher(firstline);
        Matcher mRemove = pRemove.matcher(firstline);
        Matcher mMatch= pMatch.matcher(firstline);
        
        Message msg;
        
        if(mStats.matches()){
            msg = new StatsMessage(block);
            
        }else if(mSuggest.matches()){		// suggest
            msg = new SuggestMessage(block);
            
        }else if(mResult.matches()){
            String resultString = mResult.group(2);
            System.out.println("InfoTranslator:result:" + resultString);
            
            if(resultString.indexOf("forfeits") > -1){
                msg = new ResultForfeitMessage(block);
                
            }else if(firstline.indexOf("resigns.}") > -1){
                msg = new ResultResignMessage(block);
                
            }else if(firstline.indexOf("adjourned.}") > -1){
                msg = new ResultAdjournMessage(block);

            }else{
                msg = new ResultNormalMessage(block);
            }
            
        }else if(firstline.startsWith("You can check your score with the score command")){
            msg = new ScoreModeMessage(block);
            
        }else if(mRemove.matches()){
            msg = new RemoveVertexMessage(block);
            
        }else if(firstline.indexOf("updates the match request") > -1){
            msg = new MatchUpdateMessage(block);

        }else if(mMatch.matches()){
            msg = new MatchRequestMessage(block);
            
        }else if(firstline.indexOf("declines your request for a match") > -1 || 
                firstline.indexOf("があなたの対局申し込みを断りました。") > -1){
            msg = new DeclineMatchMessage(block);
            
        }else if(firstline.indexOf("Creating match") > -1){
            System.out.println("InfoTranslator: CreatingMatchMessage");
            msg = new CreatingMatchMessage(block);
            
        }else if(firstline.indexOf("would like to adjourn the game.") > -1 ||
                firstline.indexOf("Your opponent requests an adjournment.") > -1){
            msg = new AdjournRequestMessage(block);
            
        }else if(firstline.indexOf("再開待ち時間が過ぎました。") > -1){
            msg = new DeclareMessage(block);
            
        }else{
            System.err.println("error:InfoTranslator:" + firstline);
            
            msg = new RawMessage(block);
        }
        
        return msg;
    }
}