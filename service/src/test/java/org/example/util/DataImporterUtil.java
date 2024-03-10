package org.example.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@UtilityClass
public class DataImporterUtil {

    public static void dataInit(AnnotationConfigApplicationContext context) {
        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        TestDataImporter.importData(sessionFactory);
    }
}
