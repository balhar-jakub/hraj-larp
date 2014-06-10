create sequence hraj_email_authentication_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

create table email_authentication (
  id integer DEFAULT nextval('hraj_email_authentication_id_seq'::regclass) NOT NULL,
  auth_token text,
  user_id integer,


  FOREIGN KEY(user_id) references hraj_user(id) ON UPDATE CASCADE ON DELETE RESTRICT,
  PRIMARY KEY(id)
);
