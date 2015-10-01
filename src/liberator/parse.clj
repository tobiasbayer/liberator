(ns liberator.parse
  (:require [clojure.data.json :as json]))

(defmulti parse-request-entity (fn [ctx] (get-in ctx [:headers "content-type"])))

(defmethod parse-request-entity "application/json" [ctx]
  (if-let [body (:body (:request ctx))]
    {:request-entity (json/read-str body)}
    true))

(defmethod parse-request-entity :default [ctx]
  (if-let [body (:body (:request ctx))]
    {:request-entity body}
    true))
