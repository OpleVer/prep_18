(function() {
    'use strict';

    angular
        .module('prep18App')
        .controller('ActasDeleteController',ActasDeleteController);

    ActasDeleteController.$inject = ['$uibModalInstance', 'entity', 'Actas'];

    function ActasDeleteController($uibModalInstance, entity, Actas) {
        var vm = this;

        vm.actas = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Actas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
