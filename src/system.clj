(ns system
  (:require [integrant.core :as ig]
            [next.jdbc :as jdbc]
            [ring.adapter.jetty :refer [run-jetty]]
            [reitit.ring :as ring]))

(def config
  {:adapter/jetty {:handler (ig/ref :handler/run-app) :port 3000}
   :handler/run-app {:db (ig/ref :database.sql/connection)}
   :database.sql/connection {:dbtype "sqlite" :dbname "test_db"}})

(defn do-stuff [_]
  {:status 200
   :body   "I did stuff"})

(defn app [db]
  (ring/ring-handler
   (ring/router
    ["/api"
     ["/do-stuff" {:handler do-stuff}]]
      ;; router data affecting all routes
    {:data {:db db}})))


(defmethod ig/init-key :adapter/jetty [_ {:keys [handler] :as opts}]
  (run-jetty handler (-> opts (dissoc handler) (assoc :join? false))))

(defmethod ig/init-key :handler/run-app [_ {:keys [db]}]
  (app db))

(defmethod ig/init-key :database.sql/connection [_ db-spec]
  (let [conn (jdbc/get-datasource db-spec)]
    ;; do stuff with connection on app startup
    conn))

(defmethod ig/halt-key! :adapter/jetty [_ server]
  (.stop server))

(defn -main []
  ;; Run with clojure -m system
  (ig/init config))