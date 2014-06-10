create table hraj_role (
  id int ,
  user_id int not null,
  role text,

  FOREIGN KEY (user_id) REFERENCES hraj_user(id),
  PRIMARY KEY (id)
)