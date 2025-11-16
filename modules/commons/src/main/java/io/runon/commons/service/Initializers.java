package io.runon.commons.service;

import io.runon.commons.config.Config;
import io.runon.commons.utils.ExceptionUtils;
import io.runon.commons.utils.PriorityUtils;
import io.runon.commons.utils.packages.classes.ClassSearch;
import io.runon.commons.utils.time.RunningTime;
import io.runon.commons.utils.time.TimeUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author macle
 */
@Slf4j
public class Initializers {

    public static void init() {
        String initializerPackagesValue = Config.getConfig("initializer.package", "io.runon");
        String [] initializerPackages = initializerPackagesValue.split(",");
        init(initializerPackages);
    }


    public static void init(String [] initializerPackages) {
        RunningTime time = new RunningTime();

        String initializerPackagesValue = Config.getConfig("nlp.initializer.package", "io.runon");

        ClassSearch search = new ClassSearch();
        search.setInPackages(initializerPackages);
        Class<?> [] inClasses = {Initializer.class};
        search.setInClasses(inClasses);
        List<Class<?>> classes = search.search();

        List<Initializer> initializerList = new LinkedList<>();

        for (Class<?> cl : classes) {
            try {
                Initializer initializer = (Initializer) cl.newInstance();
                initializerList.add(initializer);

            } catch (Exception e) {
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }

        log.debug("initializer size: " + initializerList.size());
        if (initializerList.isEmpty()) {
            log.debug("init empty: " + TimeUtils.getTimeValue(time.getRunningTime()));
            return;
        }
        Comparator<Initializer> initializerSort = (i1, i2) -> {
            int seq1 = PriorityUtils.getSeq(i1.getClass());
            int seq2 = PriorityUtils.getSeq(i2.getClass());
            return Integer.compare(seq1, seq2);
        };
        Initializer[] array  = initializerList.toArray(new Initializer[0]);
        Arrays.sort(array, initializerSort);

        for(Initializer initializer: array ){
            try {
                log.debug("initializer: " + initializer.getClass().getName());
                initializer.init();
            }catch(Exception e){
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }

        log.debug("Initializer all init complete: " + TimeUtils.getTimeValue(time.getRunningTime()));
    }

}
