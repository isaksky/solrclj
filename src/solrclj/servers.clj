(ns solrclj.servers
  ^{:doc "A clojure library for Apache Solr." :author "Matt Lehman"}
  (:import [java.io File]
           [org.apache.solr.client.solrj.impl HttpSolrServer]))

(defmulti create-solr-server :type)

(def default-embedded-config
     {:type :embedded
      :solr-config "solr.xml"
      :dir "./solr"})

(defn construct-via-reflection
  [class & params]
  (clojure.lang.Reflector/invokeConstructor
   (resolve (symbol class))
   (to-array params)))

(defn embedded-solr-server
  "Constructs an EmbeddedSolrServer"
  [container core]
  (construct-via-reflection "org.apache.solr.client.solrj.embedded.EmbeddedSolrServer"
                            container core))

(defn core-container
  "Constructs an CoreContainer"
  [dir config-file]
  (construct-via-reflection "org.apache.solr.core.CoreContainer"
                           dir config-file))

(defmethod create-solr-server :default [config]
  (let [config (merge default-embedded-config config)
        {:keys [dir solr-config core]} config
        solr-config-file (File. (File. dir) solr-config)
        container (core-container dir solr-config-file)]
    (embedded-solr-server container core)))

(defmethod create-solr-server :embedded-multi [config]
  (let [config (merge default-embedded-config config)
        container (core-container (:dir config) (File. (File. (:dir config)) (:solr-config config)))
        core-names (map #(.getName %) (.getCores container))]
    (reduce
     #(merge %1 {(keyword %2) (embedded-solr-server container %2)})
     {}
     core-names)))

(def default-http-config
     {:type :http
      :host "127.0.0.1"
      :port 8080
      :path "/solr"})

(defn create-server-url [{:keys [host port path core]}]
  (str "http://" host ":" port path (if core (str "/" core))))

(defmethod create-solr-server :http [config]
  (let [config  (merge default-http-config config)]
      (HttpSolrServer. (create-server-url config))))
