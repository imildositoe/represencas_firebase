package com.example.root.re_presencas.recycler_bin;

public class RecycledMethods {

//    private void controleMarcacao() {
//        //Completar para que seja uma query das marcacoes daquela turma, naquele dia e que sejam daquela alocacao
//        Query query = raiz.child("aula");
//        query.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                marcacaoList.clear();
//                for (DataSnapshot d : dataSnapshot.getChildren()) {
//                    final Aula aula = d.getValue(Aula.class);
//                    assert aula != null;
//                    //Teremos varias marcacoes, pois podem acontecer varias marcacoes numa so aula
//                    Query query1 = raiz.child("marcacao").orderByChild("id_aula").equalTo(aula.getId());
//                    query1.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            marcacaoList.clear();
//                            for (DataSnapshot d : dataSnapshot.getChildren()) {
//                                final Marcacao marcacao = d.getValue(Marcacao.class);
//                                assert marcacao != null;
//
//                                //Validando para que saia na tela do docente, marcacoes daquela alocacao(turma)
//                                Query query2 = raiz.child("alocacao").orderByChild("id").equalTo(aula.getId_alocacao());
//                                query2.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
//                                            final Alocacao alocacao = d.getValue(Alocacao.class);
//                                            assert alocacao != null;
//
//                                            if (marcacao.getId_inscricao().equals(inscricao.getId())) {
//                                                if (inscricao.getIdDisciplina().equals(alocacao.getId_disciplina())
//                                                        && inscricao.getPeriodo().equals(alocacao.getPeriodo())
//                                                        && inscricao.getAno().equals(alocacao.getAno())) {
//
//                                                }
//                                            }
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                }); // end
//
//                                marcacaoList.add(marcacao);
//                            }
//                            ListViewMarcacao adapter = new ListViewMarcacao(getActivity(), marcacaoList);
//                            listViewControleProf.setAdapter(adapter);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//
//                //swActivarMarcacao.setChecked(false);
//                //swActivarMarcacao.setText("Selar Marcação"); //tem problemas este
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

}
