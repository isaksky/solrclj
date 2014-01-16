(defproject solrclj "0.4.3-SNAPSHOT"
  :description "A clojure library for using Apache Solr."
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.apache.solr/solr-solrj "4.6.0"]
                 [org.slf4j/jcl-over-slf4j "1.7.5"]
                 [ch.qos.logback/logback-classic "1.0.13"]]
  :dev-dependencies [[org.apache.solr/solr-core "4.6.0"]
                                  [javax.servlet/servlet-api "2.5"]
                                  [commons-io/commons-io "2.4"]
                                  [org.mortbay.jetty/jetty "6.1.26"]]
  :profiles {:dev {:dependencies [[org.apache.solr/solr-core "4.3.1"]
                                  [javax.servlet/servlet-api "2.5"]
                                  [commons-io/commons-io "2.4"]
                                  [org.mortbay.jetty/jetty "6.1.26"]]}})
