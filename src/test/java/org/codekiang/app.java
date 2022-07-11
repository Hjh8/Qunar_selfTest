package org.codekiang;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class app {

    private final List<String> list1 = Arrays.asList("Google", "java", "guava");
    private final List<String> list2 = Arrays.asList("python", "c", null);

    @Test
    public void test(){
        System.out.println("a 啊。；".length());
    }

    @Test
    public void testJoin(){
        String join = Joiner.on("#").join(list1);
        System.out.println(join);
        Splitter on = Splitter.on("#");
        System.out.println(on.splitToList(join));
    }

    @Test
    public void testJoinWithNull(){
        String join = Joiner.on("#").skipNulls().join(list2);
        System.out.println(join);
        String aaa = Joiner.on("#").useForNull("aaa").join(list2);
        System.out.println(aaa);
    }

    @Test
    public void StringsTest(){
        System.out.println(Strings.nullToEmpty("ppnull"));
    }

    @Test
    public void OptionalTest(){
        Integer a = null;
        Optional<Integer> of = Optional.fromNullable(a);
        System.out.println(of.orNull());
    }


}
