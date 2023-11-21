## A senha aberta é transformada em bcrypt, utilizando o site: https://bcrypt-generator.com/
REPLACE INTO usuarios (id, login, senha)
VALUES (
        1,
        "thiago@hotmail.com",
        "$2a$12$G38EzNaEJfwnNL8AVcrLc.hX/HWz6CRN3fwySIX1tt9tZvDu1byBi"
    );
REPLACE INTO usuarios (id, login, senha)
VALUES (
        2,
        "ana.souza@voll.med",
        "$2a$12$ajvckJq02fF/p7J2teBuOOBgZ8ZwMMZbHJjLInmbi71fVd.Fhs6F."
    );
REPLACE INTO usuarios (id, login, senha)
VALUES (
        3,
        "test.test@voll.med",
        "$2a$12$ajvckJq02fF/p7J2teBuOOBgZ8ZwMMZbHJjLInmbi71fVd.Fhs6F."
    );    