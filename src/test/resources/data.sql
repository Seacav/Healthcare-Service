insert into user 
values 
('1', 'admin', '$2a$10$rueqioXUH/UU3ed4y8P5Y.4.lfj4KG6PZ05O/RIDu/8xAFZV/x4me', 'ROLE_ADMIN', 'AdminF', 'AdminL'),
('2', 'ivanova_i', '$2a$10$rueqioXUH/UU3ed4y8P5Y.4.lfj4KG6PZ05O/RIDu/8xAFZV/x4me', 'ROLE_DOCTOR', 'Irina', 'Ivanova'),
('3', 'fedorov_n', '$2a$10$rueqioXUH/UU3ed4y8P5Y.4.lfj4KG6PZ05O/RIDu/8xAFZV/x4me', 'ROLE_DOCTOR', 'Nikolay', 'Fedorov');

insert into treatment
values
('1', 'DRUG', 'Acetaminophen'),
('2', 'DRUG', 'Adderall');

insert into patient
values
('1', 'John', 'Smith', 'Hypertension', '115348', 'ivanova_i', 'TREATED');