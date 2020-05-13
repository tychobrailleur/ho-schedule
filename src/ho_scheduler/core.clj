(ns ho-scheduler.core
  (:require [clj-http.client :as client])
  (:import [org.jsoup Jsoup])
  (:gen-class))


(def hattrick-url "https://www.hattrick.org/en/")
(def ho-server-url "https://UNF6X7OJB7PFLVEQ.anvil.app/_/private_api/HN4JZ6UMWUM7I4PTILWZTJFD/league/supported")

(defn -main [& _args]
  (let [doc (-> (Jsoup/connect hattrick-url)
                .get)
        element (-> (.select doc "#online")
                    .text)
        m (last (re-matches #".*week ([0-9]+)" element))]
    (when (>= (Integer/parseInt m) 14)
      (println
       (:body (client/get
               ho-server-url
               {:accept :json}))))))
