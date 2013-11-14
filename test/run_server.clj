(ns run-server
  (:use [solrclj.test-helpers]
        [solrclj-test]
        [solrclj]))

(def test-http-rss-conf (assoc test-http-conf :core "rss"))

(defn test-ping-rss []
  (with-server [s (solr-server test-http-rss-conf)]
    (let [r (ping s)]
      (:status r))))

(defn -main []
  (with-jetty-solr 
    (test-ping-rss)
    (while true
      (Thread/sleep 60000))))

