
/*
 * Carrot2 Project
 * Copyright (C) 2002-2005, Dawid Weiss
 * Portions (C) Contributors listed in carrot2.CONTRIBUTORS file.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the CVS checkout or at:
 * http://www.cs.put.poznan.pl/dweiss/carrot2.LICENSE
 */

package com.stachoodev.carrot.filter.lingo;

import com.dawidweiss.carrot.core.local.linguistic.Language;
import com.dawidweiss.carrot.util.jdom.JDOMHelper;

import com.stachoodev.carrot.filter.lingo.common.*;

import org.apache.log4j.Logger;

import org.jdom.Element;

import java.io.InputStream;

import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This filter clusters given documents using a multilingual version of an
 * LSI-based clustering algorithm.
 */
public class MultilingualLsiClustererRequestProcessor
    extends com.dawidweiss.carrot.filter.FilterRequestProcessor {
    /**
     * Logger
     */
    protected static final Logger logger = Logger.getLogger(MultilingualLsiClustererRequestProcessor.class);

    /**
     * Languages
     */
    private Map languages = new HashMap();

    /**
     * Filters Carrot2 XML data.
     *
     * @param carrotData A valid InputStream to search results data as
     *        specified in the Manual. This filter also accepts additional
     *        keywords in the input XML data.
     * @param request Http request which caused this processing (not used in
     *        this filter)
     * @param response Http response for this request
     * @param params A map of parameters sent before data stream (unused in
     *        this filter)
     */
    public void processFilterRequest(InputStream carrotData,
        HttpServletRequest request, HttpServletResponse response, Map params)
        throws Exception {
        try {
            // parse input data (must be UTF-8 encoded).
            Element root = parseXmlStream(carrotData, "UTF-8");

            // Prepare data
            MultilingualClusteringContext clusteringContext = new MultilingualClusteringContext(new HashMap());

            Language [] languages = getLanguages( params );
            
            // Parameters
            clusteringContext.setParameters(params);
            clusteringContext.setLanguages(languages);

            // Snippets			
            List documentList = JDOMHelper.getElements("searchresult/document", root);

            if (documentList == null) {
                // save the output.
                serializeXmlStream(root, response.getOutputStream(), "UTF-8");

                return;
            }

            JDomClusterStructureHelpers.addSnippets(clusteringContext,
                documentList);

            // Query
            clusteringContext.setQuery(root.getChildText("query"));

            // Cluster !
            Cluster[] clusters = clusteringContext.cluster();

            // detect any group elements and remove them
            root.removeChildren("group");

            // Create the output XML
            for (int i = 0; i < clusters.length; i++) {
                JDomClusterStructureHelpers.addToElement(root, clusters[i]);
            }

            // save the output.
            serializeXmlStream(root, response.getOutputStream(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param stemmerClasses
     */
    private Language [] getLanguages(Map parameters) {
        ArrayList languages = new ArrayList();

        synchronized (this.languages) {
            for (Iterator keys = parameters.keySet().iterator(); keys.hasNext();) {
                String key = (String) keys.next();
    
                boolean proceed = false;
                
                if (key.startsWith("stemmer.")) {
                    logger.warn(
                        "Stemmers are no longer supported in the " +
                        "parameters. Use 'language.xxx' objects instead.");
                    proceed = true;
                }
    
                if (proceed || key.startsWith("language.")) {
                    String stemmerClass = (String) ((List) parameters.get(key)).get(0);
    
                    if (!this.languages.containsKey(stemmerClass)) {
                        try {
                            Class cl = this.getClass().getClassLoader().loadClass(stemmerClass);
                            Language lang = (Language) cl.newInstance();
                            this.languages.put( stemmerClass, lang );
                            languages.add( lang );
                        } catch (ClassNotFoundException e) {
                            logger.error("Cannot instantiate language: " + stemmerClass, e);
                        } catch (InstantiationException e) {
                            logger.error("Cannot instantiate language: " + stemmerClass, e);
                        } catch (IllegalAccessException e) {
                            logger.error("Cannot instantiate language: " + stemmerClass, e);
                        }
                    } else {
                        // language found. reuse.
                        languages.add( this.languages.get(stemmerClass) );
                    }
                }
            }
        }

        return (Language[]) languages.toArray(new Language[languages.size()]);
    }

    /**
     * Initialize servlet config.
     *
     * @see com.dawidweiss.carrot.util.AbstractRequestProcessor#setServletConfig(javax.servlet.ServletConfig)
     */
    public void setServletConfig(ServletConfig servletConfig) {
        super.setServletConfig(servletConfig);
    }
}
