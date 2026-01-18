create table app_user (
  id uuid primary key,
  username varchar(100) not null unique,
  password varchar(200) not null,
  role varchar(20) not null,
  enabled boolean not null,
  created_at timestamp with time zone not null
);

create table book (
  id uuid primary key,
  title varchar(200) not null,
  status varchar(20) not null,
  created_at timestamp with time zone not null,
  updated_at timestamp with time zone not null,
  version bigint
);

create table loan (
  id uuid primary key,
  book_id uuid not null,
  borrower_id uuid not null,
  borrowed_at timestamp with time zone not null,
  returned_at timestamp with time zone,
  constraint fk_loan_book foreign key (book_id) references book(id),
  constraint fk_loan_user foreign key (borrower_id) references app_user(id)
);

create index idx_loan_active on loan(book_id, returned_at);
