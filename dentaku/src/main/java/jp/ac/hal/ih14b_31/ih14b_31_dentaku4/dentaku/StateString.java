package jp.ac.hal.ih14b_31.ih14b_31_dentaku4.dentaku;

/**
 * Created by yoshimurasatoshi on 2014/09/04.
 */
public enum StateString {

    /**
     * 電卓のテキストエリアの状況を定義
     * 左辺,右辺が入力されている状況,結果が出力されている状況,オペランドが入力された状況
     */
     Input_Left,
     Input_Right,
     Output_Number,
     Input_Operand,
     Memory_Recall,
     ZERO_DIVIDE_ERROR,
     MAX_ERROR,
     MIN_ERROR,
     ERROR_ALL;

}