package com.lockservice;

import com.lockservice.core.LockModel;
import com.lockservice.db.LockDAO;
import com.lockservice.resources.AcquireLockResource;
import com.lockservice.resources.ReleaseLockResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LockServiceApplication extends Application<LockServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new LockServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "LockService";
    }

    private final HibernateBundle<LockServiceConfiguration> hibernateBundle
            = new HibernateBundle<LockServiceConfiguration>(LockModel.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(LockServiceConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(final Bootstrap<LockServiceConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final LockServiceConfiguration configuration,
                    final Environment environment) {

        final LockDAO lockDAO = new LockDAO(hibernateBundle.getSessionFactory());

        environment.jersey().register(new AcquireLockResource(lockDAO));
        environment.jersey().register(new ReleaseLockResource(lockDAO));
    }

}
