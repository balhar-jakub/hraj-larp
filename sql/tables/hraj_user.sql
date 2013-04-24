create sequence hraj_user_id_seq;

create table hraj_user (
  id int unique,
  name text not null,
  last_name text not null,
  user_name text not null,
  password text not null,
  email text not null,
  phone text not null,
  gender int not null,
  mail_information boolean,
  variable_symbol text not null,

  PRIMARY KEY(id)
);

ALTER TABLE hraj_user
ALTER COLUMN id
SET DEFAULT NEXTVAL('hraj_user_id_seq');
