package jp.ac.hal.ih14b_31.ih14b_31_dentaku4.dentaku;

/**
 * Created by yoshimurasatoshi on 2014/09/04.
 * ユーザから電卓へのイベント通知
 */
public interface EventTypeListenerInterface {

    /***
     * 数値ボタンが押されたことを通知する,引数はボタンの数字
     */
    public void onInputNumber( String numberText );

    /**
     * =が押された事を通知する
     */
    public void onInputEqual(  );

    /**
     * オペランドが押された事を通知する
     */
    public void onInputOperand(String operand);

    /**
     * Cが押されたことを通知する
     */
    public void onInputClear();

    /**
     * BCが押された事を通知
     */
    public void onInputBuckSpace();

    /**
     * .が押されたことを通知
     */
    public void onInputDot();

    /*
    * ACが押されたことを通知
     */
    public void onInputAllClear();

    /**
     * MCが押されたことを通知
     */
    public void onInputMemoryClear();

    /**
     * メモリー　＋　かメモリー -が押された事を通知
     */
    public void onInputMemoryOperand( String text );

    /**
     * MRが押されたこと通知
     */
    public void onInputMemoryRecall();

}
