package com.standard.banyan.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author dingchengfeng
 */
@Slf4j
public abstract class BaseCmdRunner implements ApplicationRunner {
    protected abstract String getCmd();

    protected abstract void doRun(ApplicationArguments args);

    protected boolean enable() {
        return true;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!enable()) {
            return;
        }
        List<String> cmdList = args.getNonOptionArgs();
        if (CollectionUtils.isEmpty(cmdList)) {
            return;
        }
        if (!cmdList.contains(getCmd())) {
            return;
        }
        String argsStr = String.join("|", args.getSourceArgs());
        log.info("Running with {}", argsStr);
        log.info("cmd = {}", cmdList);
        log.info("options {}", args.getOptionNames());

        log.info("运行 {}", getCmd());
        doRun(args);
    }
}
