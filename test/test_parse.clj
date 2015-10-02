(ns test-parse
  (:use liberator.core
        midje.sweet
        checkers
        [ring.mock.request :only [request]]
        [clojure.data.json :only [write-str]]))

(def request-entity (atom nil))

(facts "about entity parsing"
  (fact "it parses a json entity"
    (let [r (resource :allowed-methods [:post]
                      :handle-created (fn [ctx] (reset! request-entity (:request-entity ctx)) "created"))
          resp (r (-> (request :post "/" )
                      (ring.mock.request/body (write-str {:foo "bar"}))
                      (ring.mock.request/content-type "application/json")))]
      resp => (is-status 201)
      @request-entity => {:foo "bar"}))
  (against-background (before :facts (reset! request-entity nil))))
