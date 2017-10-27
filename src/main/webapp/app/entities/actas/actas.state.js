(function() {
    'use strict';

    angular
        .module('prep18App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('actas', {
            parent: 'entity',
            url: '/actas',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Actas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/actas/actas.html',
                    controller: 'ActasController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('actas-detail', {
            parent: 'actas',
            url: '/actas/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Actas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/actas/actas-detail.html',
                    controller: 'ActasDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Actas', function($stateParams, Actas) {
                    return Actas.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'actas',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('actas-detail.edit', {
            parent: 'actas-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/actas/actas-dialog.html',
                    controller: 'ActasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Actas', function(Actas) {
                            return Actas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('actas.new', {
            parent: 'actas',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/actas/actas-dialog.html',
                    controller: 'ActasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                distrito: null,
                                seccion: null,
                                tipo_acta: null,
                                tipo_casilla: null,
                                imagen: null,
                                votos_1: null,
                                votos_2: null,
                                votos_3: null,
                                validacion: null,
                                estatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('actas', null, { reload: 'actas' });
                }, function() {
                    $state.go('actas');
                });
            }]
        })
        .state('actas.edit', {
            parent: 'actas',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/actas/actas-dialog.html',
                    controller: 'ActasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Actas', function(Actas) {
                            return Actas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('actas', null, { reload: 'actas' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('actas.delete', {
            parent: 'actas',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/actas/actas-delete-dialog.html',
                    controller: 'ActasDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Actas', function(Actas) {
                            return Actas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('actas', null, { reload: 'actas' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
