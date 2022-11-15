package com.example.progetto1.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return new Class[]
                {
                        WebApplicationContextConfig.class   //specifica quale sia la classe di configurazione del progetto
                };
    }

    @Override
    protected String[] getServletMappings()
    {
        return new String[] { "/" };   //le chiamate vengono gestite dal dispatcher servlet. Mettendo un path specifico, esso sarà gestito direttamente dal dispatcher servlet
    }

}
