CREATE TABLE schedule(
                         pair_number INT,
                         auditory VARCHAR(6),
                         date TIMESTAMP,
                         group_name TEXT,
                         lesson TEXT,
                         lesson_type TEXT,
                         teacher TEXT,
                         PRIMARY KEY (pair_number, auditory, date, group_name)
);