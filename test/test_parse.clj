(ns test-parse
  (:use liberator.core
        [liberator.representation :only (ring-response)]
        midje.sweet
        checkers
        [ring.mock.request :only [request header]]
        [clojure.data.json :only [write-str]]))

(facts "about entity parsing"
  (fact "it parses a json entity"
    (let [r (resource :allowed-methods [:post]
                      :handle-created (fn [ctx] (println ctx)))
          resp (r (-> (request :post "/" )
                      (ring.mock.request/body (write-str {:foo "bar"}))
                      (ring.mock.request/content-type "application/json")))]
      resp => (is-status 201))))
