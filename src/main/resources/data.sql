-- Users
-- ToDo: remove all users except admin and change the password
INSERT INTO users (is_enabled, email, password, role, username)
VALUES (true, 'lazy@strong.com', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'USER', 'cptlazy99');

INSERT INTO users (is_enabled, email, password, role, username)
VALUES (true, 'pok@bess.com', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'USER', 'porgy123');

INSERT INTO users (is_enabled, email, password, role, username)
VALUES (true, 'tik@groot.com', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'ADMIN', 'trekkie76');

-- Games
INSERT INTO games (game_name, game_year_of_release, game_publisher, game_is_original, system_name, user_name)
VALUES ('Sonic', 1991, 'Sega', true, 'MegaDrive', 'porgy123');

INSERT INTO games (game_name, game_year_of_release, game_publisher, game_is_original, system_name, user_name)
VALUES ('Samurai ShoDown 2', 1994, 'SNK', true, 'Neo-Geo', 'porgy123');

INSERT INTO games (game_name, game_year_of_release, game_publisher, game_is_original, system_name, user_name)
VALUES ('Mario Bros 3', 1988, 'Nintendo', false, 'NES', 'porgy123');

--  Game systems
INSERT INTO game_systems (game_system_name, game_system_brand, game_system_year_of_release, is_ready_to_play, user_name)
VALUES ('Dreamcast', 'SEGA', 1999, true, 'porgy123');

INSERT INTO game_systems (game_system_name, game_system_brand, game_system_year_of_release, is_ready_to_play, user_name)
VALUES ('Neo-Geo', 'SNK', 1990, true, 'porgy123');

INSERT INTO game_systems (game_system_name, game_system_brand, game_system_year_of_release, is_ready_to_play, user_name)
VALUES ('Super Nintendo', 'Nintendo', 1990, true, 'porgy123');

-- Game conditions
INSERT INTO game_conditions (has_case, has_manual, has_scratches, has_stickers, has_writing)
VALUES (true, false, true, false, false);

INSERT INTO game_conditions (has_case, has_manual, has_scratches, has_stickers, has_writing)
VALUES (true, true, false, false, false);

INSERT INTO game_conditions (has_case, has_manual, has_scratches, has_stickers, has_writing)
VALUES (false, true, true, false, false);

-- Game system conditions
INSERT INTO game_system_conditions (has_box, has_cables, is_modified)
VALUES (true, true, false);

INSERT INTO game_system_conditions (has_box, has_cables, is_modified)
VALUES (false, true, false);

INSERT INTO game_system_conditions (has_box, has_cables, is_modified)
VALUES (true, true, true);
