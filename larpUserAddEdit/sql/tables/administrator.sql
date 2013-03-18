create table administrator (
  id int unique,

  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES hraj_user(id)
);