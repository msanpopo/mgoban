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

package sgf.property;

public enum PropertyType {
    UNKNOWN,
    MOVE,
        /*
        move
        Properties of this type concentrate on the move made, not on
        the position arrived at by this move.
        Move properties must not be mixed with setup properties within
        the same node.
        Note: it's bad style to have move properties in root nodes.
        (it isn't forbidden though)
         */
    SETUP,
        /*
        setup
        Properties of this type concentrate on the current position.
        Setup properties must not be mixed with move properties within
        a node.
         */
    ROOT,
        /*
        root
        Root properties may only appear in root nodes. Root nodes are
        the first nodes of gametrees, which are direct descendants from a
        collection (i.e. not gametrees within other gametrees).
        They define some global 'attributes' such as board-size, kind
        of game, used file format etc.
         */
    GAME_INFO,
        /*
        game-info
        Game-info properties provide some information about the game
        played (e.g. who, where, when, what, result, rules, etc.).
        These properties are usually stored in root nodes.
        When merging a set of games into a single gametree, game infos
        are stored at the nodes where a game first becomes distinguishable
        from all other games in the tree.
         
    A node containing game-info properties is called a game-info node.
    There may be only one game-info node on any path within the tree,
    i.e. if some game-info properties occur in one node there may not be
    any further game-info properties in following nodes:
    a) on the path from the root node to this node.
    b) in the subtree below this node.
         */
    NO_TYPE,
        /*
        -
        no type. These properties have no special types and may appear
        anywhere in a collection.
         */
    
    //INHERIT;
        /*
         * 本当はプロパティータイプではないけど、この属性が付いたプロパティーは全て NO_TYPE で
         * 他のタイプと競合しないのでここに入れる。
         */
        /*
        Properties having this attribute affect not only the node containing
        these properties but also ALL subsequent child nodes as well until
        a new setting is encountered or the setting gets cleared.
        I.e. once set all children (of this node) inherit the values of the
        'inherit' type properties.
        E.g. VW restricts the view not only of the current node, but
        of all successors nodes as well. Thus a VW at the beginning of a
        variation is valid for the whole variation tree.
        Inheritance stops, if either a new property is encountered and those
        values are inherited from now on, or the property value gets cleared,
        typically by an empty value, e.g. VW[].
         */
    
    
    
}
