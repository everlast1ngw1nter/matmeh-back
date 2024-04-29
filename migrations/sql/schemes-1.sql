CREATE TABLE schedule(
                         pair_number INT,
                         auditory VARCHAR(6),
                         date TIMESTAMP,
                         PRIMARY KEY (pair_number, auditory, date)
);