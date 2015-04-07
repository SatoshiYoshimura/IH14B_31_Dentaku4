package jp.ac.hal.ih14b_31.ih14b_31_dentaku4.dentaku;

import java.util.EventListener;

/**
 * Created by yoshimurasatoshi on 2014/09/04.
 */
public interface StateListenerInterface extends EventListener {

    /**
    * 電卓が左辺が入力状態である事を通知する
    */
    public void inputingLeft();

    /**
     * 電卓が右辺が入力状態である事を通知する
     */
    public void inputingRight();

    /**
     * 電卓がオペランドが押されている状態である事を通知する
     */
    public void inputingOperand();

    /**
     * 電卓が結果が出力されている状態である事を通知する
     */
    public void outputNumber();

    /**
     * MRが押された状態である事を通知
     */
    public void inputingMemoryRecall();

    /**
     * ゼロDIVerror
     */
    public void onZeroDivError();


}
