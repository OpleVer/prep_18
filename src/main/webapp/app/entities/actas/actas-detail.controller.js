(function() {
    'use strict';

    angular
        .module('prep18App')
        .controller('ActasDetailController', ActasDetailController);

    ActasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Actas'];

    function ActasDetailController($scope, $rootScope, $stateParams, previousState, entity, Actas) {
        var vm = this;

        vm.actas = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('prep18App:actasUpdate', function(event, result) {
            vm.actas = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
