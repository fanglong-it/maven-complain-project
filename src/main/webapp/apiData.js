new Vue({
	el: '#app',
	data: {
		provinces: [],
		districts: [],
		wards: [],
		selectedProvince: '',
		selectedDistrict: '',
		selectedWard: '',
		base_url: window.base_url,
	},
	created() {
		this.loadProvinces();
	},
	methods: {
		loadProvinces() {
			axios.get(`${base_url}/api/provinces`) // Adjust this endpoint as necessary
				.then(response => {
					this.provinces.push(response.data); // Assuming response.data is an array of provinces
					console.log(this.provinces);
				})
				.catch(error => console.error('Error fetching provinces:', error));
		},

		// Method to get districts and wards when a province is selected
		updateDistrictsAndWards() {
			const selectedProvince = this.provinces.find(province => province.name === this.selectedProvince);
			if (selectedProvince) {
				this.districts = selectedProvince.districts; // Set districts from the selected province
				this.selectedDistrict = ''; // Reset selected district
				this.wards = []; // Clear wards since district is changed
			}
		},

		// Method to get wards when a district is selected
		updateWards() {
			const selectedDistrict = this.districts.find(district => district.name === this.selectedDistrict);
			if (selectedDistrict) {
				this.wards = selectedDistrict.wards; // Set wards from the selected district
			} else {
				this.wards = []; // Clear wards if no district is selected
			}
		}
	}
});
