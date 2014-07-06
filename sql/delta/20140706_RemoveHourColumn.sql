alter table game drop column hour;

update game set mail_prohibition = false where mail_prohibition is null;