package org.codekiang.simulateShell.commands;

import java.util.List;

public interface Command<T> {

    T getResult(Object lastRes, String option, List<String> params) throws Exception;
}
