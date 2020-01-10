import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TicTacToeGame {
    private Scanner scan;
    private Set set_all_board;  // 设置所有位置
    private Set[] set_has_ox_pieces; // 双方已经落子的位置
    private int haveRightToGo;  //有走棋权一方的索引
    private Set[] set_win_case; //所有赢棋的情况
    private int last_step; // 最后一步走在哪里

    public TicTacToeGame()
    {
        scan = new Scanner(System.in);

        set_all_board = new HashSet();
        for(int i=0; i<9; i++) set_all_board.add(i);

        set_has_ox_pieces = new Set[2];
        set_has_ox_pieces[0] = new HashSet();
        set_has_ox_pieces[1] = new HashSet();
        haveRightToGo = 0;

        int[][] WIN_CASE = {{0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

        set_win_case = new Set[WIN_CASE.length];
        for(int i=0; i<WIN_CASE.length; i++){
            set_win_case[i] = new HashSet();
            for(int j=0; j<WIN_CASE[i].length; j++) {
                set_win_case[i].add(WIN_CASE[i][j]);
            }
        }
        last_step = -1;  // 还没有最后一步
    }

    private void show()
    {
        for(int i=0; i<9; i++){
            if(i%3==0) System.out.println();
            char c = '.';
            if(set_has_ox_pieces[0].contains(i)) c = 'x';
            if(set_has_ox_pieces[1].contains(i)) c = 'o';
            if(i == last_step) c -= 32;
            System.out.print(c + " ");
        }
        System.out.println();
    }

    private int getPlayerInput()
    {
        char c = 'x';
        if(haveRightToGo==1) c = 'o';
        for(;;){
            try{
                System.out.print("请" + c + "方走棋：");
                int n = Integer.parseInt(scan.nextLine());
                if(n<0) return n; // 认输
                if(set_all_board.contains(n)==false) throw new Exception("");
                if(set_has_ox_pieces[0].contains(n) || set_has_ox_pieces[1].contains(n)) throw new Exception("");
                return n;
            }
            catch(Exception e){
                System.out.println("不正确的输入位置");
            }
        }
    }


    // 0,1：有胜负分出，2：全部下完没分胜负，平局，-1：尚无胜负，可以继续下
    private int test_finish()
    {
        for(int i=0; i<set_win_case.length; i++){
            if(set_has_ox_pieces[0].containsAll(set_win_case[i])) return 0; // x胜
            if(set_has_ox_pieces[1].containsAll(set_win_case[i])) return 1; // o胜
        }

        if(set_has_ox_pieces[0].size() + set_has_ox_pieces[1].size() == set_all_board.size()) return 2; //平局

        return -1; //继续
    }

    public void start()
    {
        for(;;){
            show();
            int r = test_finish(); //检测是否棋局应该结束
            if(r==0) System.out.println("x方胜出！");
            if(r==1) System.out.println("o方胜出！");
            if(r==2) System.out.println("平局结束！");
            if(r>=0) break;

            int playerInput = getPlayerInput();

            if(playerInput<0){
                char c = haveRightToGo==0 ? 'x' : 'o';
                System.out.println(c + "方放弃！游戏结束！");
                break;
            }

            set_has_ox_pieces[haveRightToGo].add(playerInput);
            last_step = playerInput;
            haveRightToGo = (haveRightToGo+1)%2;
        }
    }

    private static void welcome()
    {
        System.out.println("3*3棋盘用 0-8 的数字表示：");
        System.out.println("0 1 2");
        System.out.println("3 4 5");
        System.out.println("6 7 8");
        System.out.println("x方与o方轮流走棋，x方先走，连成一条直线的一方获胜");
    }

    public static void main(String[] args)
    {
        welcome();
        TicTacToeGame ticTacToeGame = new TicTacToeGame();
        ticTacToeGame.start();
    }
}
