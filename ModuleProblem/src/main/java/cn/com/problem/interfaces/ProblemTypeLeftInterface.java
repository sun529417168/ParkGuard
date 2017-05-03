package cn.com.problem.interfaces;

import java.util.List;

import cn.com.problem.bean.ProblemTypeLeft;


/**
 * 文件名：ProblemTypeLeftInterface
 * 描    述：获取一级节点信息
 * 作    者：stt
 * 时    间：2017.02.17
 * 版    本：V1.0.0
 */

public interface ProblemTypeLeftInterface {
    void getTypeLeft(List<ProblemTypeLeft> problemTypeLeftList);
}
