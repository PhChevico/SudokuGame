DROP TABLE IF EXISTS INT_boards cascade ;
DROP TABLE IF EXISTS INT_saveAndLoad cascade ;
DROP TABLE IF EXISTS INT_leaderboard cascade ;
DROP TABLE IF EXISTS INT_boardSolved cascade ;
DROP TABLE IF EXISTS INT_playable_board cascade ;
DROP TABLE IF EXISTS int_savedboards cascade ;
DROP TABLE IF EXISTS INT_game cascade ;
DROP TABLE IF EXISTS INT_allLoads cascade ;
DROP SEQUENCE IF EXISTS seq_game_id;


CREATE TABLE IF NOT EXISTS INT_game (
                                        game_id INTEGER CONSTRAINT pk_INT_game PRIMARY KEY,
                                        player_name VARCHAR(255) CONSTRAINT nn_player_name NOT NULL,
                                        date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP SEQUENCE IF EXISTS seq_game_id;
CREATE SEQUENCE IF NOT EXISTS seq_game_id START WITH 1 INCREMENT BY 1;

create TABLE INT_boards(
                           game_board CHAR (81)
                                                               NOT NULL ,
                           solved_board CHAR(81)
                                                               NOT NULL ,
                           level NUMERIC (1)
                               CONSTRAINT pk_level PRIMARY KEY NOT NULL
                               CONSTRAINT ch_level CHECK ( level between 1 and 7),
                           difficulty NUMERIC (2,1)
                               CONSTRAINT ch_difficulty CHECK (difficulty between 0 and 3),
                           multiplier NUMERIC (3)
                               CONSTRAINT ch_multiplier CHECK (multiplier between 1 and 1000)
);


create TABLE IF NOT EXISTS INT_leaderboard(
                                              player_name VARCHAR(50)
                                                  CONSTRAINT pk_player_name PRIMARY KEY
                                                  NOT NULL ,
                                              date VARCHAR(50) DEFAULT CURRENT_DATE
                                                  NOT NULL ,
                                              score NUMERIC (8)
                                                  NOT NULL
);




DROP TABLE IF EXISTS INT_saveAndLoad;
create TABLE IF NOT EXISTS INT_saveAndLoad(
                                              player_name VARCHAR(50)
                                                  CONSTRAINT fk_player_name REFERENCES int_leaderboard(player_name),
                                              time_played numeric (3),
                                              time_remaining numeric (3),
                                              game_id INTEGER CONSTRAINT fk_game_id references int_game(game_id),
                                              level NUMERIC (1)
                                                  CONSTRAINT fk_level references int_boards(level),
                                              CONSTRAINT pk_saveAndLoad PRIMARY KEY (player_name, level)
);


DROP TABLE IF EXISTS INT_boardSolved;
create TABLE IF NOT EXISTS INT_boardSolved(
                                              level NUMERIC (1)
                                                  CONSTRAINT fk_level references int_boards(level),
                                              x NUMERIC (1),
                                              y NUMERIC (1),
                                              numberInCell NUMERIC (1),
                                              CONSTRAINT pk_boardSolved PRIMARY KEY (level, x, y)
);


DROP TABLE IF EXISTS INT_allLoads;
create TABLE IF NOT EXISTS INT_allLoads(
                                              game_id INTEGER
                                               CONSTRAINT fk_game_id references int_game (game_id),
                                              level NUMERIC (1)
                                                  CONSTRAINT fk_level references int_boards(level),
                                              x NUMERIC (1),
                                              y NUMERIC (1),
                                              numberInCell NUMERIC (1)
);


-- This helped me fill the table int_boardSolved
DO $$
    DECLARE
        lv INTEGER = 1;
        i INTEGER;
        j INTEGER;
        char_index INTEGER;
        current_char TEXT;
        input_string TEXT;
    BEGIN

        FOR lv IN 1..7 LOOP
                input_string := (SELECT solved_board from int_boards WHERE level = lv);

                char_index := 1;
                FOR i IN 1..9 LOOP
                        FOR j IN 1..9 LOOP
                                -- Extract each character from the input string
                                current_char := SUBSTRING(input_string FROM char_index FOR 1);
                                -- Insert the character into the numberInCell column
                                INSERT INTO INT_boardSolved (level, x, y, numberInCell) VALUES (lv, i, j, cast(current_char as INTEGER ));
                                char_index := char_index + 1; -- Move to the next character in the string
                            END LOOP;
                    END LOOP;
            END LOOP ;
    END $$;

-- Normally it only filters 501 lines, so the command bellow allows you to see the other missing lines from the filter
--SELECT * FROM int_boardsolved LIMIT 1000 OFFSET 501;







DROP TABLE IF EXISTS INT_playable_board;
create TABLE IF NOT EXISTS INT_playable_board(
                                                 level NUMERIC (1)
                                                     CONSTRAINT fk_level references int_boards(level),
                                                 x NUMERIC (1),
                                                 y NUMERIC (1),
                                                 numberInCell NUMERIC (1)
);

-- This helped me fill the table int_current_game
DO $$
    DECLARE
        lv INTEGER = 1;
        i INTEGER;
        j INTEGER;
        char_index INTEGER;
        current_char TEXT;
        input_string TEXT;
    BEGIN
        input_string := (SELECT game_board from int_boards WHERE level = lv);
        char_index := 1;
        FOR i IN 1..9 LOOP
                FOR j IN 1..9 LOOP
                        -- Extract each character from the input string
                        current_char := SUBSTRING(input_string FROM char_index FOR 1);
                        -- Insert the character into the numberInCell column
                        INSERT INTO INT_playable_board (level, x, y, numberInCell) VALUES (lv, i, j, cast(current_char as INTEGER ));
                        char_index := char_index + 1; -- Move to the next character in the string
                    END LOOP;
            END LOOP;
    END $$;

-- Normally it only filters 501 lines, so the command bellow allows you to see the other missing lines from the filter
-- SELECT * FROM int_current_game LIMIT 1000 OFFSET 501;



INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier)
VALUES ( '000260701680070090190004500820100040004602900050003028009300074040050036703018000', '435269781682571493197834562826195347374682915951743628519326874248957136763418259', 1, 1, 100);

INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier)
VALUES ( '100489006730000040000001295007120600500703008006095700914600000020000037800512004', '152489376739256841468371295387124659591763428246895713914637582625948137873512964', 2, 1.2, 200);

INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier)
VALUES ( '020608000580009700000040000370000500600000004008000013000020000009800036000306090', '123678945584239761967145328372461589691583274458792613836924157219857436745316892', 3, 1.4, 300);

INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier)
VALUES ( '000600400700003600000091080000000000050180003000306045040200060903000000020000100', '581672439792843651364591782438957216256184973179326845845219367913768524627435198', 4, 1.6, 400);

INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier)
VALUES ( '200300000804062003013800200000020390507000621032006000020009140601250809000001002', '276314958854962713913875264468127395597438621132596487325789146641253879789641532', 5, 1.8, 500);

INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier)
VALUES ( '000000200080007090602000500070060000000901000000020040005000603090400070006000000', '957613284483257196612849537178364952524971368369528741845792613291436875736185429', 6, 2, 600);

INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier)
VALUES ( '020000000000600003074080000000003002080040010600500000000010780500009000000000040', '126437958895621473374985126457193862983246517612578394269314785548769231731852649', 7, 2.2, 700);