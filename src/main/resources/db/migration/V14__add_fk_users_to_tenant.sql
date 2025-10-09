-- Adiciona a FK de tenant para users
ALTER TABLE public.tenant
	ADD CONSTRAINT tenant_fk_user FOREIGN KEY (user_id)
	REFERENCES public.users(id) MATCH SIMPLE
	ON UPDATE NO ACTION
	ON DELETE NO ACTION;

-- Cria índice para acelerar consultas por user_id
CREATE INDEX idx_tenant_user_id
	ON public.tenant(user_id);
