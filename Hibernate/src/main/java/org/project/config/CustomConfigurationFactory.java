package org.project.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.net.URI;

@Plugin(name = "CustomConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(50)
@SuppressWarnings("unused")
public class CustomConfigurationFactory extends ConfigurationFactory {

    static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
        AppenderComponentBuilder console
                = builder.newAppender("stdout", "Console");

        builder.add(console);

        AppenderComponentBuilder file
                = builder.newAppender("log", "File");
        file.addAttribute("fileName", "target/logging.log");

        builder.add(file);

        AppenderComponentBuilder rollingFile
                = builder.newAppender("rolling", "RollingFile");
        rollingFile.addAttribute("fileName", "rolling.log");
        rollingFile.addAttribute("filePattern", "rolling-%d{MM-dd-yy}.log.gz");

        builder.add(rollingFile);

        FilterComponentBuilder flow = builder.newFilter(
                "MarkerFilter",
                Filter.Result.ACCEPT,
                Filter.Result.DENY);
        flow.addAttribute("marker", "FLOW");

        console.add(flow);

        LayoutComponentBuilder standard
                = builder.newLayout("PatternLayout");
        standard.addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");

        console.add(standard);
        file.add(standard);
        rollingFile.add(standard);

        RootLoggerComponentBuilder rootLogger
                = builder.newRootLogger(Level.ERROR);
        rootLogger.add(builder.newAppenderRef("stdout"));

        builder.add(rootLogger);

        LoggerComponentBuilder logger = builder.newLogger("com", Level.DEBUG);
        logger.add(builder.newAppenderRef("log"));
        logger.addAttribute("additivity", false);

        builder.add(logger);

        ComponentBuilder triggeringPolicies = builder.newComponent("Policies")
                .addComponent(builder.newComponent("CronTriggeringPolicy")
                        .addAttribute("schedule", "0 0 0 * * ?"))
                .addComponent(builder.newComponent("SizeBasedTriggeringPolicy")
                        .addAttribute("size", "100M"));

        rollingFile.addComponent(triggeringPolicies);

        return builder.build();
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[]{"*"};
    }
}
