console.log("Dosya yüklendi - ilk satır");
document.addEventListener('DOMContentLoaded', () => {
    console.log("DOMContentLoaded event tetiklendi");
});

window.addEventListener('load', () => {
    console.log("Sayfa tamamen yüklendi");
    new AppointmentSearch();
});

class AppointmentSearchState {
    constructor() {
        this._state = {
            cityId: null,
            districtId: null,
            hospitalId: null,
            clinicId: null,
            doctorId: null
        };

        console.log("AppointmentSearchState - yapıcı metot");
    }

    get currentState() {
        return {...this._state};
    }

    updateState(key, value) {
        if (!this._state.hasOwnProperty(key)) {
            throw new Error(`Invalid state key: ${key}`);
        }
        this._state[key] = value;

        // Reset dependent states
        const keys = Object.keys(this._state);
        const index = keys.indexOf(key);
        if (index < keys.length - 1) {
            for (let i = index + 1; i < keys.length; i++) {
                this._state[keys[i]] = null;
            }
        }
    }
}

class SelectComponent {
    constructor(selectElement, options = {}) {
        this.selectElement = selectElement;
        this.defaultText = options.defaultText || 'Seçiniz';
        this.onChange = options.onChange || (() => {});
        this.disabled = options.disabled || false;

        this.initialize();
    }

    initialize() {
        this.selectElement.innerHTML = `<option selected disabled value="">${this.defaultText}</option>`;
        this.selectElement.disabled = this.disabled;
        this.selectElement.addEventListener('change', () => this.onChange(this.selectElement.value));
    }

    updateOptions(items, valueKey = 'id', textKey = 'name') {
        this.selectElement.innerHTML = `<option selected disabled value="">${this.defaultText}</option>`;

        items.forEach(item => {
            const option = document.createElement('option');
            option.value = item[valueKey];
            option.textContent = item[textKey];
            this.selectElement.appendChild(option);
        });

        this.selectElement.disabled = items.length === 0;
    }

    reset() {
        this.selectElement.innerHTML = `<option selected disabled value="">${this.defaultText}</option>`;
        this.selectElement.disabled = true;
    }
}

class AppointmentSearch {
    constructor() {
        this.form = document.getElementById('appointmentSearchForm');
        this.citySelect = document.getElementById('city');
        this.districtSelect = document.getElementById('district');
        this.hospitalSelect = document.getElementById('hospital');
        this.clinicSelect = document.getElementById('clinic');
        this.doctorSelect = document.getElementById('doctor');
        this.loader = document.getElementById('loader');
        this.errorAlert = document.getElementById('errorAlert');

        this.initEvents();
        this.loadCities();

        console.log("AppointmentSearch - yapıcı metot");
    }

    initEvents() {
        this.citySelect.addEventListener('change', () => this.loadDistricts());
        this.districtSelect.addEventListener('change', () => this.loadHospitals());
        this.hospitalSelect.addEventListener('change', () => this.loadClinics());
        this.clinicSelect.addEventListener('change', () => this.loadDoctors());
        this.form.addEventListener('submit', (e) => this.handleSubmit(e));
    }

    async fetchData(url) {
        this.showLoader();
        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error('Ağ bağlantısı 200 OK dönmedi!');
            return await response.json();
        } catch (error) {
            this.showError(error.message);
            throw error;
        } finally {
            this.hideLoader();
        }
    }

    async loadCities() {
        try {
            const data = await this.fetchData('/api/v1/appointments/search/hierarchy');
            this.populateSelect(this.citySelect, data.cities);
        } catch (error) {
            console.error('Şehir verileri yüklenemedi hata: ', error);
        }
    }

    async loadDistricts() {
        const cityId = this.citySelect.value;
        if (!cityId) return;

        this.resetDependentSelects(this.districtSelect, [this.hospitalSelect, this.clinicSelect, this.doctorSelect]);

        try {
            const districts = await this.fetchData(`/api/v1/appointments/search/districts/${cityId}`);
            this.populateSelect(this.districtSelect, districts);
            this.enableSelect(this.districtSelect);
        } catch (error) {
            console.error('İlçe verileri yüklenemedi hata: ', error);
        }
    }

    async loadHospitals() {
        const districtId = this.districtSelect.value;
        if (!districtId) return;

        this.resetDependentSelects(this.hospitalSelect, [this.clinicSelect, this.doctorSelect]);

        try {
            const hospitals = await this.fetchData(`/api/v1/appointments/search/hospitals/${districtId}`);
            this.populateSelect(this.hospitalSelect, hospitals);
            this.enableSelect(this.hospitalSelect);
        } catch (error) {
            console.error('Hastane verileri yüklenemedi hata: ', error);
        }
    }

    async loadClinics() {
        const hospitalId = this.hospitalSelect.value;
        if (!hospitalId) return;

        this.resetDependentSelects(this.clinicSelect, [this.doctorSelect]);

        try {
            const clinics = await this.fetchData(`/api/v1/appointments/search/clinics/${hospitalId}`);
            this.populateSelect(this.clinicSelect, clinics);
            this.enableSelect(this.clinicSelect);
        } catch (error) {
            console.error('Klinik verileri yüklenemedi hata: ', error);
        }
    }

    async loadDoctors() {
        console.log("loadDoctors çağrıldı test"); // EKLENDİ
        const clinicId = this.clinicSelect.value;
        if (!clinicId) return;

        this.resetDependentSelects(this.doctorSelect, []);

        try {
            const doctors = await this.fetchData(`/api/v1/appointments/search/doctors/${clinicId}`);
            console.log("Gelen doktorlar test: ", doctors); // EKLENDİ
            /*this.populateSelect(this.doctorSelect, doctors);
            this.enableSelect(this.doctorSelect);*/
            this.populateSelect(this.doctorSelect, doctors, 'id', 'fullName');
            this.enableSelect(this.doctorSelect);
        } catch (error) {
            console.error('Doktor verileri yüklenemedi hata: ', error);
        }
    }

    // Diğer load metodları benzer şekilde implemente edilecek...

    /*populateSelect(selectElement, items) {
        selectElement.innerHTML = items.map(item =>
            `<option value="${item.id}">${item.name}</option>`
        ).join('');
    }*/

    populateSelect(selectElement, items) {
        selectElement.innerHTML = `<option selected disabled value="">Seçiniz</option>`; // <-- EKLENDİ

        items.forEach(item => {
            const option = document.createElement('option');
            option.value = item.id;
            option.textContent = item.name;
            selectElement.appendChild(option);
        });

        selectElement.disabled = items.length === 0;
    }

    populateSelect(selectElement, items, valueKey = 'id', textKey = 'name') {
        selectElement.innerHTML = '<option selected disabled value="">Lütfen seçim yapınız</option>';

        items.forEach(item => {
            const option = document.createElement('option');
            option.value = item[valueKey];
            option.textContent = item[textKey];
            selectElement.appendChild(option);
        });
    }

    resetDependentSelects(mainSelect, dependentSelects) {
        dependentSelects.forEach(select => {
            select.innerHTML = `<option selected disabled value="">Önce ${mainSelect.previousElementSibling.textContent.trim()} seçiniz</option>`;
            select.disabled = true;
        });
    }

    enableSelect(selectElement) {
        selectElement.disabled = false;
        selectElement.selectedIndex = 0;
    }

    showLoader() {
        if (this.loader) this.loader.style.display = 'block';
    }

    hideLoader() {
        if (this.loader) this.loader.style.display = 'none';
    }

    showError(message) {
        if (this.errorAlert) {
            this.errorAlert.textContent = `Hata: ${message}`;
            this.errorAlert.style.display = 'block';
            setTimeout(() => this.errorAlert.style.display = 'none', 5000);
        }
    }

    handleSubmit(event) {
        event.preventDefault();

        if (this.form.checkValidity()) {
            const formData = new FormData(this.form);
            const params = new URLSearchParams(formData);
            window.location.href = `/appointments/search/results?${params.toString()}`;
        } else {
            this.form.classList.add('was-validated');
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new AppointmentSearch();
});