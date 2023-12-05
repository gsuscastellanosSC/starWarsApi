-- Crear la tabla FILM si no existe en ambiente dev
CREATE TABLE IF NOT EXISTS FILM (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       episode_id VARCHAR(10) NOT NULL,
                                       title VARCHAR(500) NOT NULL,
                                       release_date DATE NOT NULL
);

-- Insertar datos por defecto en la tabla FILM
INSERT INTO FILM (
    episode_id,
    title,
    release_date
)
VALUES (
           '5',
           'The Empire Strikes Back',
           '1980-05-17'
       );
