(function() {
    'use strict';

    angular
        .module('prep18App')
        .controller('ActasDialogController', ActasDialogController);

    ActasDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Actas'];

    function ActasDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Actas) {
        var vm = this;

        vm.actas = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.actas.id !== null) {
                Actas.update(vm.actas, onSaveSuccess, onSaveError);
            } else {
                Actas.save(vm.actas, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prep18App:actasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
