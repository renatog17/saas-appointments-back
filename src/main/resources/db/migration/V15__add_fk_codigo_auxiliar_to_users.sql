-- Adiciona a FK de users para codigo_auxiliar
ALTER TABLE public.users
ADD CONSTRAINT users_fk_codigo_auxiliar
FOREIGN KEY (codigo_auxiliar_id)
REFERENCES public.codigo_auxiliar(id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION;

-- Cria índice para acelerar consultas por codigo_auxialiar_id
CREATE INDEX idx_users_codigo_auxiliar_id
ON public.users(codigo_auxiliar_id);
