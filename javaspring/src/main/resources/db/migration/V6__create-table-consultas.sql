create table consultas(

    id bigint not null auto_increment,
    medicos bigint not null,
    pacientes bigint not null,
    data datetime not null,

    primary key(id),
    constraint fk_consultas_medicos foreign key(medicos) references medicos(id),
    constraint fk_consultas_paciente foreign key(pacientes) references pacientes(id)

);