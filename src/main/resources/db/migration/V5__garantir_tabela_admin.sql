CREATE TABLE IF NOT EXISTS admin (
                                     usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE CASCADE
);

-- Insere admin inicial para login
INSERT INTO usuario (nome, email, senha, tipo)
SELECT 'Admin LauMiau', 'admin@laumiau.com', 'admin123', 'admin'
WHERE NOT EXISTS (
    SELECT 1 FROM usuario WHERE email = 'admin@laumiau.com'
);

INSERT INTO admin (usuario_id)
SELECT id FROM usuario WHERE email = 'admin@laumiau.com'
                         AND NOT EXISTS (
        SELECT 1 FROM admin
    );