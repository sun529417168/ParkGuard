package cn.com.problem.interfaces;

/**
 * 文件名：ProblemTypeInterface
 * 描    述：问题上报的类型选择的接口回调
 * 作    者：stt
 * 时    间：2017.02.17
 * 版    本：V1.0.0
 */

public interface ProblemTypeInterface {
    void getProblemType(int type, String typeName);
    void clearColor();
}
