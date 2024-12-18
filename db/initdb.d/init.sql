-- create member schema
create schema member;

-- create uuid option
create extension if not exists "uuid-ossp";

-- create member table;
create table member.member (
    id uuid primary key default uuid_generate_v4(),
    username varchar(50) not null,
    password varchar(256),
    email varchar(100),
    created_at timestamp(6) default current_timestamp(6),
    updated_at timestamp(6)
);

create unique index username_idx on member.member(username);

-- create roles table
create table member.roles (
    id bigserial primary key ,
    member_id uuid  references member.member(id),
    role varchar(50) not null
);

-- create transaction schema
create schema transaction;

-- create transactionType table
create table transaction.transaction_type(
    id bigserial primary key ,
    type varchar(50)
);

-- create category table
create table transaction.category(
    id bigserial primary key ,
    category varchar(50)
);

-- create transaction table
create table transaction.transaction (
    id bigserial primary key,
    member_id uuid,
    type_id bigserial not null,
    category_id bigserial not null,
    amount bigint not null,
    description varchar(100),
    date timestamp(6) default current_timestamp(6)
);

create index member_id_idx on transaction.transaction(member_id);

-- create budget schema
create schema budget;

-- create budget table
create table budget.budget (
    id bigserial primary key ,
    member_id uuid not null ,
    month date not null,
    amount bigint not null,
    created_at timestamp(6) default current_timestamp(6)
);

create index budget_member_id_idx on budget.budget(member_id);
