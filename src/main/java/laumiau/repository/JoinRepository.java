package laumiau.repository;

import jakarta.persistence.EntityManager;
import java.util.List;

public class JoinRepository {

    private final EntityManager em;

    public JoinRepository(EntityManager em) {
        this.em = em;
    }

    // INNER JOIN — somente adocoes de animais e clientes
    public List<Object[]> innerJoinAdocoes() {
        try {
            return em.createNativeQuery("""
                    SELECT ad.id, an.nome, an.especie, u.nome, ad.data_adocao, ad.termo_assinado
                    FROM adocoes ad
                    INNER JOIN animal an ON ad.animal_id = an.id
                    INNER JOIN cliente c ON ad.cliente_id = c.usuario_id
                    INNER JOIN usuarios u ON c.usuario_id = u.id
                    """).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro no INNER JOIN: " + e.getMessage());
        }
    }

    // LEFT JOIN — todos os animais incluindo nao adotados
    public List<Object[]> leftJoinAnimais() {
        try {
            return em.createNativeQuery("""
                    SELECT an.id, an.nome, an.especie, an.status, ad.id, ad.data_adocao
                    FROM animal an
                    LEFT JOIN adocoes ad ON an.id = ad.animal_id
                    """).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro no LEFT JOIN: " + e.getMessage());
        }
    }

    // RIGHT JOIN — todas as adocoes mesmo se o animal for removido
    public List<Object[]> rightJoinAdocoes() {
        try {
            return em.createNativeQuery("""
                    SELECT an.id, an.nome, ad.id, ad.data_adocao
                    FROM animal an
                    RIGHT JOIN adocoes ad ON an.id = ad.animal_id
                    """).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro no RIGHT JOIN: " + e.getMessage());
        }
    }

    // FULL JOIN — todos os animais e todas as adocoes
    public List<Object[]> fullJoinAnimaisAdocoes() {
        try {
            return em.createNativeQuery("""
                    SELECT an.id, an.nome, ad.id, ad.data_adocao
                    FROM animal an
                    FULL JOIN adocoes ad ON an.id = ad.animal_id
                    """).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro no FULL JOIN: " + e.getMessage());
        }
    }

    public void imprimirResultados(List<Object[]> resultados, String[] colunas) {
        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado encontrado.");
            return;
        }
        for (Object[] row : resultados) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < row.length; i++) {
                sb.append(colunas[i]).append(": ").append(row[i]);
                if (i < row.length - 1) sb.append(" | ");
            }
            System.out.println(sb);
        }
    }
}