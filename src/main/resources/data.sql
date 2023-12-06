-- Users
INSERT INTO users (is_enabled, profile_is_private, email, password, role, username)
VALUES (true, true, 'lazy@strong.com', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'USER', 'cptlazy');

INSERT INTO users (is_enabled, profile_is_private, email, password, role, username)
VALUES (true, true, 'pok@bess.com', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'USER', 'porgy');

INSERT INTO users (is_enabled, profile_is_private, email, password, role, username)
VALUES (true, true, 'tik@groot.com', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'ADMIN', 'tokkkie');

-- Games
INSERT INTO games (game_name, game_rating, game_review)
VALUES ('Sonic', 5, 'Fast game');

INSERT INTO games (game_name, game_rating, game_review)
VALUES ('Zelda', 5, 'Best game');

INSERT INTO games (game_name, game_rating, game_review)
VALUES ('Mario', 5, 'Fun game');

--  Game systems
INSERT INTO game_systems (game_system_name, game_system_review, game_system_rating)
VALUES ('Dreamcast', 'very good', '3');

INSERT INTO game_systems (game_system_name, game_system_review, game_system_rating)
VALUES ('Neo-Geo', 'the godfather', '3');

INSERT INTO game_systems (game_system_name, game_system_review, game_system_rating)
VALUES ('Playstation', 'top seller', '2');

-- Game conditions
INSERT INTO game_conditions (has_case, has_manual, is_complete_in_box)
VALUES (true, true, true);

INSERT INTO game_conditions (has_case, has_manual, is_complete_in_box)
VALUES (true, false, true);

INSERT INTO game_conditions (has_case, has_manual, is_complete_in_box)
VALUES (true, false, false);

-- Game system conditions
INSERT INTO game_system_conditions (has_box, has_cables, is_modified)
VALUES (true, true, false);

INSERT INTO game_system_conditions (has_box, has_cables, is_modified)
VALUES (false, true, false);

INSERT INTO game_system_conditions (has_box, has_cables, is_modified)
VALUES (true, true, true);
