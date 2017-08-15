package leedcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XCY on 2017/5/29.
 * 先判断行列，然后判断方块
 */
public class _36_Valid_Sudoku {
    public boolean isValidSudoku(char[][] board) {
        boolean flag=true;
        for(int i =0;i<board.length;i++){//行,列
            Map map=new HashMap();
            Map map1=new HashMap();
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]!='.'){
                    if(map.containsKey(board[i][j]))
                    {
                        return false;
                    }
                    else
                        map.put(board[i][j],1);
                }
                if(board[j][i]!='.'){
                    if(map1.containsKey(board[j][i]))
                    {
                        return false;
                    }
                    else
                        map1.put(board[j][i],1);
                }
            }
        }
        for(int i =0;i<board.length-2;i+=3){
            for(int j=0;j<board[0].length;j+=3){
                Map map2=new HashMap();
                for(int t=i;t<i+3;t++){
                    for(int m=j;m<j+3;m++){
                        if(board[t][m]!='.'){
                            if(map2.containsKey(board[t][m]))
                            {return false;}
                            else
                                map2.put(board[t][m],1);
                        }
                    }
                }
            }
        }
        return true;
    }
}
