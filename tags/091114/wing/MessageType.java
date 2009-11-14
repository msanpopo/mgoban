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

package wing;

import wing.message.AdjournRequestMessage;
import wing.message.CreatingMatchMessage;
import wing.message.DeclineMatchMessage;
import wing.message.GamesMessage;
import wing.message.KibitzMessage;
import wing.message.MatchRequestMessage;
import wing.message.MatchUpdateMessage;
import wing.message.MoveMessage;
import wing.message.RawMessage;
import wing.message.RemoveVertexMessage;
import wing.message.RestoreMessage;
import wing.message.ResultAdjournMessage;
import wing.message.ResultForfeitMessage;
import wing.message.ResultNormalMessage;
import wing.message.ResultResignMessage;
import wing.message.SavedMessage;
import wing.message.SayMessage;
import wing.message.ScoreModeMessage;
import wing.message.SgfMessage;
import wing.message.StatsMessage;
import wing.message.StatusMessage;
import wing.message.SuggestMessage;
import wing.message.TellMessage;
import wing.message.ThistMessage;
import wing.message.WhoMessage;
import wing.message.YellMessage;

public enum MessageType {
    UNKNOWN(null),
    
    ADJOURN_REQUEST(AdjournRequestMessage.class),
    CREATING_MATCH(CreatingMatchMessage.class),
    DECLINE_MATCH(DeclineMatchMessage.class),
    GAMES(GamesMessage.class),
    KIBITZ(KibitzMessage.class),
    MATCH_REQUEST(MatchRequestMessage.class),
    MATCH_UPDATE(MatchUpdateMessage.class),
    MOVE(MoveMessage.class),
    REMOVE_VERTEX(RemoveVertexMessage.class),
    RESTORE(RestoreMessage.class),
    RESULT_ADJOURN(ResultAdjournMessage.class),
    RESULT_FORFEIT(ResultForfeitMessage.class),
    RESULT_NORMAL(ResultNormalMessage.class),
    RESULT_RESIGN(ResultResignMessage.class),
    SAVED(SavedMessage.class),
    SAY(SayMessage.class),
    SCORE_MODE(ScoreModeMessage.class),
    SGF(SgfMessage.class),
    STATS(StatsMessage.class),
    STATUS(StatusMessage.class),
    SUGGEST(SuggestMessage.class),
    TELL(TellMessage.class),
    THIST(ThistMessage.class),
    WHO(WhoMessage.class),
    Yell(YellMessage.class),
    
    RAW(RawMessage.class);
    
    private final Class messageClass;
    
    private MessageType(Class messageClass){
        if(messageClass == null){
            this.messageClass = RawMessage.class;
        }else{
            this.messageClass = messageClass;
        }
    }
    
    
}