package org.project.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.io.IOException;
import java.net.URI;

@Plugin(name = "simple", category = ConfigurationFactory.CATEGORY)
@Order(50)
@SuppressWarnings("unused")
public class Log4jConfigurationFactory extends ConfigurationFactory {

    static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
        LayoutComponentBuilder layout = builder.newLayout("PatternLayout")
                .addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");

        FilterComponentBuilder filter = builder.newFilter("MarkerFilter", Filter.Result.ACCEPT, Filter.Result.DENY);
        filter.addAttribute("marker", "FLOW");

        AppenderComponentBuilder console = builder.newAppender("Stdout", "Console");
        console.add(layout);
//        console.add(filter);
        builder.add(console);

        ComponentBuilder triggeringPolicies = builder.newComponent("Policies")
                .addComponent(builder.newComponent("CronTriggeringPolicy")
                        .addAttribute("schedule", "0 0 0 * * ?"))
                .addComponent(builder.newComponent("SizeBasedTriggeringPolicy")
                        .addAttribute("size", "100M"));

        AppenderComponentBuilder rollingFile = builder.newAppender("rolling", "RollingFile");
        rollingFile.addAttribute("fileName", "logs/rolling.log");
        rollingFile.addAttribute("filePattern", "logs/archive/rolling-%d{MM-dd-yy}.log.gz");
        rollingFile.add(layout);
        rollingFile.addComponent(triggeringPolicies);
        builder.add(rollingFile);

        AppenderComponentBuilder file = builder.newAppender("FileSystem", "File");
        file.addAttribute("fileName", "logs/logging.log");
        file.addAttribute("append", false);
        file.add(layout);
        builder.add(file);

        LoggerComponentBuilder logger = builder.newLogger("org.project", Level.INFO);
        logger.add(builder.newAppenderRef("Stdout"));
        logger.add(builder.newAppenderRef("FileSystem"));
        logger.add(builder.newAppenderRef("rolling"));
        logger.addAttribute("additivity", false);
        builder.add(logger);

        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.INFO);
        rootLogger.add(builder.newAppenderRef("Stdout"));
        rootLogger.add(builder.newAppenderRef("FileSystem"));
        rootLogger.add(builder.newAppenderRef("rolling"));
        rootLogger.addAttribute("additivity", false);
        builder.add(rootLogger);

        try {
            builder.writeXmlConfiguration(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[] { "*" };
    }
}
