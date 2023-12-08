-- Users
INSERT INTO users (is_enabled, profile_is_private, email, password, role, username)
VALUES (true, true, 'lazy@strong.com', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'USER', 'cptlazy');

INSERT INTO users (is_enabled, profile_is_private, email, password, role, username)
VALUES (true, true, 'pok@bess.com', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'USER', 'porgy');

INSERT INTO users (is_enabled, profile_is_private, email, password, role, username)
VALUES (true, true, 'tik@groot.com', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'ADMIN', 'tokkkie');

-- Games
INSERT INTO games (game_name, game_year_of_release, game_publisher, game_is_original)
VALUES ('Sonic', 1991, 'Sega', true);

INSERT INTO games (game_name, game_year_of_release, game_publisher, game_is_original)
VALUES ('Samurai ShoDown 2', 1994, 'SNK', true);

INSERT INTO games (game_name, game_year_of_release, game_publisher, game_is_original)
VALUES ('Mario Bros 3', 1988, 'Nintendo', false);

--  Game systems
INSERT INTO game_systems (game_system_name, game_system_brand, game_system_year_of_release, is_ready_to_play)
VALUES ('Dreamcast', 'SEGA', 1999, true);

INSERT INTO game_systems (game_system_name, game_system_brand, game_system_year_of_release, is_ready_to_play)
VALUES ('Neo-Geo', 'SNK', 1990, true);

INSERT INTO game_systems (game_system_name, game_system_brand, game_system_year_of_release, is_ready_to_play)
VALUES ('Super Nintendo', 'Nintendo', 1990, true);

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
